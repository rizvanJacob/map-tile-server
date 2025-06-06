import "./App.css";
import Map from "./components/Map";
import LayerList from "./components/LayerList";
import OSM from "ol/source/OSM";
import { useState } from "react";
import type Layer from "ol/layer/Layer";
import TileLayer from "ol/layer/Tile";

const App = () => {
  const [layers, setLayers] = useState<Layer[]>([
    new TileLayer({
      source: new OSM(),
      visible: true,
    }),
  ]);

  return (
    <div className="w-screen h-screen flex">
      <Map layers={layers} />
      <LayerList layers={layers} setLayers={setLayers} />
    </div>
  );
};

export default App;
