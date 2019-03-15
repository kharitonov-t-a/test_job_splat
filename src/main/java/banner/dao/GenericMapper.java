package banner.dao;

import org.springframework.jdbc.core.RowMapper;

public interface GenericMapper<T> extends RowMapper<T> {
}
