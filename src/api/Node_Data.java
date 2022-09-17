package api;

public class Node_Data implements NodeData {
    private final int key;
    private Geo_Location point;
    private double weight;
    private String info;
    private int tag;




    public Node_Data(int key,Geo_Location loc){
        this.key = key;
        this.point=loc;
        this.info="";
        this.tag=0;
        this.weight = 0;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.point;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.point = (Geo_Location)p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    @Override
    public boolean equals(Object ot){
        if (ot == this) return true;
        if (ot == null || ot.getClass() != this.getClass()) return false;
        NodeData other = (NodeData) ot;
        return this.getKey()== other.getKey() && this.getWeight()== other.getWeight() && this.point.equals(other.getLocation()) && this.tag == other.getTag() && this.getInfo().equals(other.getInfo());
     }
}
