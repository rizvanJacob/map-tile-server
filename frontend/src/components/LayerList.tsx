// import { useCallback } from "react";
import type Layer from "ol/layer/Layer";
// import VectorLayer from "ol/layer/Vector";
// import VectorSource from "ol/source/Vector";

type Props = {
  layers: Layer[];
  setLayers: (layers: Layer[]) => void;
};

function LayerList({ layers, setLayers }: Props) {
  const toggleVisibility = (index: number) => {
    const newLayers = [...layers];
    const layer = newLayers[index];
    layer.setVisible(!layer.getVisible());
    setLayers(newLayers);
  };

  //   const handleFileUpload = useCallback(
  //     (event: React.ChangeEvent<HTMLInputElement>) => {
  //       const file = event.target.files?.[0];
  //       if (!file) return;

  //       const reader = new FileReader();
  //       reader.onload = () => {
  //         try {
  //           const vectorSource = new VectorSource();
  //           const vectorLayer = new VectorLayer({
  //             source: vectorSource,
  //             visible: true,
  //           });
  //           setLayers([...layers, vectorLayer]);
  //         } catch (e) {
  //           console.error("Failed to parse GeoJSON:", e);
  //         }
  //       };
  //       reader.readAsText(file);
  //     },
  //     [layers, setLayers]
  //   );

  return (
    <fieldset className="fieldset bg-base-100 border-base-300 rounded-box w-64 border px-4 m-2">
      <legend className="fieldset-legend">Layers</legend>
      {layers.map((layer, i) => (
        <label className="label" key={i}>
          <input
            type="checkbox"
            className="toggle toggle-sm"
            checked={layer.getVisible()}
            onChange={() => toggleVisibility(i)}
          />
          Layer {i + 1}
        </label>
      ))}
      <div className="mt-4">
        {/* <input type="file" accept=".geojson" onChange={handleFileUpload} /> */}
      </div>
    </fieldset>
  );
}

export default LayerList;
