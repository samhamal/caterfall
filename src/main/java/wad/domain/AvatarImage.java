package wad.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class AvatarImage extends AbstractPersistable<Long> {
    @Lob
    private byte[] content;
    
    private String contentType;
    private Long contentLength;
    
    @OneToOne(fetch = FetchType.LAZY)
    private Person owner;
    
    public AvatarImage() {
        
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }
    
    public void setContent(byte[] content) {
        this.content = content;
    }
    
    public byte[] getContent() {
        return content;
    }
}
