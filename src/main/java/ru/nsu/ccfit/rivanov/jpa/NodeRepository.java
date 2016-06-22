package ru.nsu.ccfit.rivanov.jpa;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.rivanov.entities.Node;
import ru.nsu.ccfit.rivanov.services.NodeDao;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, BigInteger> {
//    Node findByNodeId(BigInteger nodeId);
//    @Query("SELECT n.lon from Node n")


    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM nodes\n" +
            "WHERE ABS((earth_distance(ll_to_earth(:lat, :lon), ll_to_earth(0, 0))) - (earth_distance(ll_to_earth(nodes.lat, nodes.lon), ll_to_earth(0, 0)))) < :distance AND\n" +
            "      ABS((earth_distance(ll_to_earth(:lat, :lon), ll_to_earth(0, 90))) - (earth_distance(ll_to_earth(nodes.lat, nodes.lon), ll_to_earth(0, 90)))) < :distance")
//    @Query("SELECT n.lon from Node AS n"
//    )
    List<Node> findNodeNearPoint(@Param("lat") double lattitude,
                                   @Param("lon") double longitude,
                                   @Param("distance") double distance);
}
