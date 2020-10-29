package com.dna.tester.mutantvalidator.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DNACatalog" )
@EntityListeners(AuditingEntityListener.class)
public class DNA {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(name = "RequestDate")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    @Column(name = "DnaReceived", nullable = false)
    private String dnaReceived;

    @Column(name = "MutantResultCode")
    private int mutantResultCode;

    @Column(name = "MutantResultText")
    private String mutantResultText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getDnaReceived() {
        return dnaReceived;
    }

    public void setDnaReceived(String dnaReceived) {
        this.dnaReceived = dnaReceived;
    }

    public int getMutantResultCode() {
        return mutantResultCode;
    }

    public void setMutantResultCode(int mutantResultCode) {
        this.mutantResultCode = mutantResultCode;
    }

    public String getMutantResultText() {
        return mutantResultText;
    }

    public void setMutantResultText(String mutantResultText) {
        this.mutantResultText = mutantResultText;
    }

    public static DNABuilder builder(){
        return new DNABuilder();
    }

    public static final class DNABuilder {
        private Long id;
        private Date requestDate;
        private String dnaReceived;
        private int mutantResultCode;
        private String mutantResultText;

        private DNABuilder() {
        }

        public static DNABuilder aDNA() {
            return new DNABuilder();
        }

        public DNABuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public DNABuilder withRequestDate(Date requestDate) {
            this.requestDate = requestDate;
            return this;
        }

        public DNABuilder withDnaReceived(String dnaReceived) {
            this.dnaReceived = dnaReceived;
            return this;
        }

        public DNABuilder withMutantResultCode(int mutantResultCode) {
            this.mutantResultCode = mutantResultCode;
            return this;
        }

        public DNABuilder withMutantResultText(String mutantResultText) {
            this.mutantResultText = mutantResultText;
            return this;
        }

        public DNA build() {
            DNA dNA = new DNA();
            dNA.setRequestDate(requestDate);
            dNA.setDnaReceived(dnaReceived);
            dNA.setMutantResultCode(mutantResultCode);
            dNA.setMutantResultText(mutantResultText);
            return dNA;
        }
    }
}
