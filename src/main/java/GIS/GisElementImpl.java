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

    /**
     * tranlase method is to change given input's value
     * it means to stand at this Gis_element , and to "move" the element by a vector
     * TODO - ask if the algorithm is this.element(represented as V ) == V(x,y,z)+ Point3D(x,y,z)
     * @param vec is the value of movement translation
     */
    @Override
    public void translate(Point3D vec) {
        ((Point3D) geom).add(vec);
    }
}
