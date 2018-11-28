package GIS;

import Coords.MyCoords;
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
     * @param vec is the translated element.
     */
    @Override
    public void translate(Point3D vec) {
        MyCoords myCoords = new MyCoords();
        GIS_element element = new GisElementImpl(vec,this.data);
        Point3D translate = myCoords.vector3D(new Point3D(element.getGeom().toString()),vec);
        GIS_element translated = new GisElementImpl(translate,element.getData());
        this.geom = translated.getGeom();
        this.data = translated.getData();
    }
}
