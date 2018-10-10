package lucas.cardapioonline.Classes;

public class clpropertyLinearLayout {

    private String id;
    private Integer orientation;
    private Integer layout_width;
    private Integer layout_height;
    private Integer gravity;
    private Integer margimLeft;
    private Integer margimRight;
    private Integer margimTop;
    private Integer margimBottom;

    public clpropertyLinearLayout() {
        inicializaVariaveis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrientation() {
        return orientation;
    }

    public void setOrientation(Integer orientation) {
        this.orientation = orientation;
    }

    public Integer getLayout_width() {
        return layout_width;
    }

    public void setLayout_width(Integer layout_width) {
        this.layout_width = layout_width;
    }

    public Integer getLayout_height() {
        return layout_height;
    }

    public void setLayout_height(Integer layout_height) {
        this.layout_height = layout_height;
    }

    public Integer getGravity() {
        return gravity;
    }

    public void setGravity(Integer gravity) {
        this.gravity = gravity;
    }

    public Integer getMargimLeft() {
        return margimLeft;
    }

    public void setMargimLeft(Integer margimLeft) {
        this.margimLeft = margimLeft;
    }

    public Integer getMargimRight() {
        return margimRight;
    }

    public void setMargimRight(Integer margimRight) {
        this.margimRight = margimRight;
    }

    public Integer getMargimTop() {
        return margimTop;
    }

    public void setMargimTop(Integer margimTop) {
        this.margimTop = margimTop;
    }

    public Integer getMargimBottom() {
        return margimBottom;
    }

    public void setMargimBottom(Integer margimBottom) {
        this.margimBottom = margimBottom;
    }

    private void inicializaVariaveis() {
        id = "";
        orientation = 0;
        layout_width = 0;
        layout_height = 0;
        gravity = 0;
        margimLeft = 0;
        margimRight = 0;
        margimTop = 0;
        margimBottom = 0;
    }
}


