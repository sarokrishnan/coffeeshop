package app.model;

public class Price {


    private float tall;
    private float grande;
    private float venti;
    public Price(){}

    public Price(float tall, float grande, float venti){
        this.tall = tall;
        this.grande = grande;
        this.venti = venti;
    }

    public float getTall() {
        return tall;
    }

    public void setTall(float tall) {
        this.tall = tall;
    }

    public float getGrande() {
        return grande;
    }

    public void setGrande(float grande) {
        this.grande = grande;
    }

    public float getVenti() {
        return venti;
    }

    public void setVenti(float venti) {
        this.venti = venti;
    }
}
