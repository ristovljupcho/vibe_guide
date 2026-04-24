package com.vibe_guide.domain.place.repositories.impl;

import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.domain.place.repositories.PlacePreviewRepository;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlacePreviewRepositoryImpl implements PlacePreviewRepository {

  private static final String BASE_SQL =
      """
      SELECT
        p.id              AS id,
        p.name            AS name,
        p.description     AS description,
        p.address         AS address,
        p.rating          AS rating,
        p.primary_type    AS primary_type,
        p.price_level     AS price_level,
        COALESCE(g.image_urls, ARRAY[]::text[]) AS image_urls,
        COALESCE(ptt.top_traits, ARRAY[]::text[]) AS top_traits
      FROM place p
      LEFT JOIN place_top_traits ptt ON ptt.place_id = p.id
      LEFT JOIN (
        SELECT place_id, ARRAY_AGG(photo ORDER BY id) AS image_urls
        FROM place_gallery
        WHERE photo IS NOT NULL
        GROUP BY place_id
      ) g ON g.place_id = p.id
      """;

  private static final String TRAIT_FILTER_SQL =
      """
      WHERE p.id IN (
        SELECT pt.place_id
        FROM place_trait pt
        JOIN trait t ON t.id = pt.trait_id
        WHERE t.name IN (:traits)
        GROUP BY pt.place_id
        HAVING COUNT(DISTINCT t.id) = :traitsSize
      )
      """;

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public List<PlacePreviewResponseDTO> findAllPreviews(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection) {

    PlaceSortBy actualSortBy = sortBy != null ? sortBy : PlaceSortBy.DEFAULT;
    SortDirection actualSortDirection =
        sortDirection != null ? sortDirection : SortDirection.DESC;

    StringBuilder sql = new StringBuilder(BASE_SQL);
    Map<String, Object> params = new HashMap<>();

    if (traits != null && !traits.isEmpty()) {
      sql.append(TRAIT_FILTER_SQL);
      params.put("traits", traits);
      params.put("traitsSize", traits.size());
    }

    sql.append(" ORDER BY ")
        .append(toSqlColumn(actualSortBy))
        .append(' ')
        .append(actualSortDirection == SortDirection.DESC ? "DESC" : "ASC");

    return jdbcTemplate.query(sql.toString(), params, previewRowMapper());
  }

  @Override
  public List<PlacePreviewResponseDTO> findTopPreviews(int limit) {
    String sql = BASE_SQL + " ORDER BY p.rating DESC LIMIT :limit";
    return jdbcTemplate.query(
        sql, new MapSqlParameterSource("limit", limit), previewRowMapper());
  }

  private static RowMapper<PlacePreviewResponseDTO> previewRowMapper() {
    return (rs, rowNum) ->
        new PlacePreviewResponseDTO(
            rs.getObject("id", UUID.class),
            rs.getString("name"),
            rs.getString("description"),
            rs.getString("address"),
            rs.getDouble("rating"),
            readEnum(rs, "primary_type", PrimaryType.class),
            readEnum(rs, "price_level", PriceLevel.class),
            readStringArray(rs, "image_urls"),
            readStringArray(rs, "top_traits"));
  }

  private static <E extends Enum<E>> E readEnum(
      ResultSet rs, String column, Class<E> enumType) throws SQLException {
    String value = rs.getString(column);
    return value == null ? null : Enum.valueOf(enumType, value);
  }

  private static List<String> readStringArray(ResultSet rs, String column) throws SQLException {
    Array array = rs.getArray(column);
    if (array == null) {
      return List.of();
    }
    String[] values = (String[]) array.getArray();
    return values == null ? List.of() : Arrays.asList(values);
  }

  private static String toSqlColumn(PlaceSortBy sortBy) {
    return switch (sortBy) {
      case DEFAULT -> "p.name";
      case RATING -> "p.rating";
      case PRICE_LEVEL -> "p.price_level";
    };
  }
}
