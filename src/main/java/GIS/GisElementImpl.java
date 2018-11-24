package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class GisElementImpl implements GIS_element {

    private Geom_element geom;
    private Meta_data data;

    public GisElementImpl(Geom_element geom, Meta_data data) {
        this.geom = geom;
        this.data = data;
    }

    @Override
    public Geom_element getGeom() {
        return geom;
    }

    @Override
    public Meta_data getData() {
        return data;
    }

    @Override
    public void translate(Point3D vec) {

    }
}
