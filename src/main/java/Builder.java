public class Builder {
    private String name;
    private Integer status;

    private Builder() {
    }

    static class InnerBuilder {
        private String name;
        private Integer status;

        public InnerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InnerBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder build() {
            Builder builder = new Builder();
            builder.name = name;
            builder.status = status;
            return builder;
        }
    }

    public static InnerBuilder newBuilder() {
        return new InnerBuilder();
    }

    @Override
    public String toString() {
        return "name:" + name + "," + "status:" + status;
    }

    public static void main(String[] args) {
        Builder builder = Builder.newBuilder()
                .name("A builder")
                .status(1)
                .build();
        System.out.println(builder);
    }
}
