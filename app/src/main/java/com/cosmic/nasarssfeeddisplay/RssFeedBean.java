package com.cosmic.nasarssfeeddisplay;

/**
 * Created by anushree on 8/31/2017.
 */

public class RssFeedBean {

    private String title;
    private String link;
    private String description;
    private String imageURL;

    public RssFeedBean(String title, String link, String description, String imageURL) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RssFeedBean that = (RssFeedBean) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return imageURL != null ? imageURL.equals(that.imageURL) : that.imageURL == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageURL != null ? imageURL.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "RssFeedBean{" +
                "title='" + title + '\'' +
                '}';
    }
}

