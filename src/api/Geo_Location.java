package api;

public class Geo_Location implements GeoLocation {
    private final double x;
    private final double y;
    private final double z;

    public Geo_Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public double distance(api.GeoLocation g) {
        return Math.sqrt(Math.pow(g.x() - this.x, 2) + Math.pow(g.y() - this.y, 2) + Math.pow(g.z() - this.z, 2));
    }

    @Override
    public boolean equals(Object ot) {
        if (ot == this) return true;
        if (ot == null || ot.getClass() != this.getClass()) return false;
        GeoLocation other = (GeoLocation) ot;
        return this.x() == other.x() && this.y == other.y() && this.z == other.z();
    }
}