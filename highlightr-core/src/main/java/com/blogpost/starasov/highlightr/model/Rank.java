package com.blogpost.starasov.highlightr.model;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: starasov
 * Date: 1/5/12
 * Time: 8:20 PM
 */
@Entity
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int rank;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stream stream;

    public Rank() {
    }

    public Rank(int rank, String identifier) {
        this.id = 0L;
        this.rank = rank;
        this.identifier = identifier;
    }

    public static double toAverageRank(List<Rank> ranks) {
        Assert.notNull(ranks, "ranks parameter can't be null.");

        SummaryStatistics statistics = new SummaryStatistics();
        for (Rank rank : ranks) {
            statistics.addValue(rank.getRank());
        }

        return statistics.getMean();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
        if (!stream.hasRank(this)) {
            stream.addRank(this);
        }
    }

    public void update(Rank rank) {
        setRank(rank.getRank());
        setIdentifier(rank.getIdentifier());
    }

    @PrePersist
    @PreUpdate
    void onSave() {
        this.timestamp = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rank rank = (Rank) o;

        if (id != null ? !id.equals(rank.id) : rank.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Rank");
        sb.append("{id=").append(id);
        sb.append(", rank=").append(rank);
        sb.append(", identifier='").append(identifier).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
