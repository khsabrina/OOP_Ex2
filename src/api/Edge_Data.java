package api;

public class Edge_Data implements EdgeData{
    int src;
    int dst;
    double weight;
    int tag;
    String info;

    public Edge_Data(int src,int dst, int tag, String info){
        this.src=src;
        this.dst=dst;
        this.weight=0;
        this.tag=tag;
        this.info=info;
    }

    public Edge_Data(int src,int dst,double weight){
        this.src=src;
        this.dst=dst;
        this.weight=weight;
        this.tag=0;
        this.info="0";
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dst;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}