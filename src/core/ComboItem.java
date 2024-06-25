package core;

public class ComboItem {
    public int key;
    private String value;

    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //Combobox öğelerini ekranda göstermek için value'yı döndürüyoruz.
    @Override
    public String toString() {
        return value;
    }
}
