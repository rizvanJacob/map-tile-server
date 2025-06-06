import "./App.css";
import OSM from "ol/source/OSM";
import { useState } from "react";
import type Layer from "ol/layer/Layer";
import TileLayer from "ol/layer/Tile";
import Map from "./components/Map";
import LayerControl from "./components/LayerControl";

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
      <LayerControl layers={layers} setLayers={setLayers} />
    </div>
  );
};

export default App;
