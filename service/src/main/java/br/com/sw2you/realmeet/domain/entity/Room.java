package br.com.sw2you.realmeet.domain.entity;

public class Room {
    private Long id;
    private String name;
    private Integer seats;
    private Boolean active;

    private Room(Long id, String name, Integer seats, Boolean active) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.active = active;
    }

    public Room() {}

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (name != null ? !name.equals(room.name) : room.name != null) return false;
        if (seats != null ? !seats.equals(room.seats) : room.seats != null) return false;
        return active != null ? active.equals(room.active) : room.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", name='" + name + '\'' + ", seats=" + seats + ", active=" + active + '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Integer seats;
        private Boolean active;

        private Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder seats(Integer seats) {
            this.seats = seats;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Room build() {
            return new Room(id, name, seats, active);
        }
    }
}
