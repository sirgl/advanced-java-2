package ru.nsu.ccfit.rivanov.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.entities.Tag;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
//@Primary
public class JdbcNodeDao implements ru.nsu.ccfit.rivanov.services.NodeDao {
    private static final String NODES_TABLE = "nodes";
    private static final String ORIGINAL_ID_COLUMN = "original_id";
    private static final String ID_COLUMN = "id";
    private static final String LAT_COLUMN = "lat";
    private static final String LON_COLUMN = "lon";
    private static final String USERNAME_COLUMN = "username";
    private static final String UID_COLUMN = "uid";
    private static final String VISIBLE_COLUMN = "visible";
    private static final String VERSION_NUMBER_COLUMN = "version_number";
    private static final String CHANGESET_COLUMN = "changeset";
    private static final String TIMESTAMP_COLUMN = "timestamp";

    public static final String insertTagsSql = "INSERT INTO tags (key, value, node_id) VALUES (?, ?, ?)";

    private final JdbcOperations jdbcOperations;
    private final SimpleJdbcInsert nodesInsert;
    private final DataSource dataSource;

    @Autowired
    public JdbcNodeDao(JdbcOperations jdbcOperations, DataSource dataSource) {
        this.jdbcOperations = jdbcOperations;
        this.dataSource = dataSource;
        nodesInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(NODES_TABLE)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    @Transactional
    public void createNode(Node node) {
        if(node == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ORIGINAL_ID_COLUMN, node.getId());
        parameters.put(LAT_COLUMN, node.getLat());
        parameters.put(LON_COLUMN, node.getLon());
        parameters.put(USERNAME_COLUMN, node.getUser());
        Long uid = node.getUid() != null ? node.getUid().longValue() : null;
        parameters.put(UID_COLUMN, uid);
        parameters.put(VISIBLE_COLUMN, node.getVisible());
        Long version = node.getVersion() != null ? node.getVersion().longValue() : null;
        parameters.put(VERSION_NUMBER_COLUMN, version);
        Long changset = node.getChangeset() != null ? node.getChangeset().longValue() : null;
        parameters.put(CHANGESET_COLUMN, changset);
        parameters.put(TIMESTAMP_COLUMN, node.getTimestamp());
        long id = nodesInsert.executeAndReturnKey(parameters).longValue();

        List<Tag> tags = node.getTag();
        jdbcOperations.batchUpdate(insertTagsSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                ps.setString(1, tag.getK());
                ps.setString(2, tag.getV());
                ps.setLong(3, id);
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }
}
