package ro.mve.rest.mapper;

import org.dozer.Mapping;

public class SourceBean {

    private Long id = 123L;

    private String name = "ada milea";

    @Mapping("binaryData")
    private String data = "some binary data";

    @Mapping("pk")
    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}     