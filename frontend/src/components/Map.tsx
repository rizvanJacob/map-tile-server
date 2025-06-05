import { useEffect, useRef } from "react";
import { Map as OlMap, View } from "ol";
import "ol/ol.css";
import type Layer from "ol/layer/Layer";

type Props = {
  layers: Layer[];
};

function Map({ layers }: Props) {
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!mapRef.current) return;

    const map = new OlMap({
      target: mapRef.current,
      layers: layers,
      view: new View({
        center: [0, 0],
        zoom: 2,
      }),
    });

    return () => {
      map.setTarget(undefined);
    };
  }, [layers]);

  return <div ref={mapRef} className="map-container w-full h-full" />;
}

export default Map;
