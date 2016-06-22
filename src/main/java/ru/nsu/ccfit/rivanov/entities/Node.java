package ru.nsu.ccfit.rivanov.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "nodes")
public class Node {

    //    @Basic(fetch = FetchType.EAGER)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "node")
//    @JoinColumn(name = "node_id")
    private List<Tag> tag;

    public Node() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "original_id")
    private BigInteger originalId;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lon")
    private Double lon;
    @Column(name = "username")
    private String user;
    @Column(name = "uid")
    private BigInteger uid;
    @Column(name = "visible")
    private Boolean visible;
    @Column(name = "version_number")
    private BigInteger version;
    @Column(name = "changeset")
    private BigInteger changeset;
    @Column(name = "timestamp", columnDefinition = "TIMESTAMP WITH TIMEZONE")
    private Timestamp timestamp;

    public Node(BigInteger originalId, Double lat, Double lon, String user, BigInteger uid, Boolean visible,
                BigInteger version, BigInteger changeset, Timestamp timestamp) {
        this.originalId = originalId;
        this.lat = lat;
        this.lon = lon;
        this.user = user;
        this.uid = uid;
        this.visible = visible;
        this.version = version;
        this.changeset = changeset;
        this.timestamp = timestamp;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getOriginalId() {
        return originalId;
    }

    public void setOriginalId(BigInteger originalId) {
        this.originalId = originalId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigInteger getUid() {
        return uid;
    }

    public void setUid(BigInteger uid) {
        this.uid = uid;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getChangeset() {
        return changeset;
    }

    public void setChangeset(BigInteger changeset) {
        this.changeset = changeset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
