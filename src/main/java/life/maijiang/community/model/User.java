package life.maijiang.community.model;

public class User {
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountId='" + account_id + '\'' +
                ", token='" + token + '\'' +
                ", gmtCreate=" + gmt_create +
                ", gmtModified=" + gmt_modified +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return account_id;
    }

    public void setAccountId(String accountId) {
        this.account_id = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getGmtCreate() {
        return gmt_create;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmt_create = gmtCreate;
    }

    public Long getGmtModified() {
        return gmt_modified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmt_modified = gmtModified;
    }
}
