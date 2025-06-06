import type Layer from "ol/layer/Layer";

type Props = {
  layers: Layer[];
  setLayers: (layers: Layer[]) => void;
};

const LayerControl = ({ layers, setLayers }: Props) => {
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
    <div className="w-sm h-full flex flex-col">
      <fieldset className="fieldset bg-base-100 border-base-300 rounded-box w-64 border px-4 m-2 flex-1">
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
      </fieldset>
      <fieldset className="fieldset m-2">
        <legend className="fieldset-legend">Upload layer from file</legend>
        <input type="file" className="file-input file-input-xs" />
        <button className="btn btn-ghost btn-xs">Upload Layer</button>
      </fieldset>
    </div>
  );
};

export default LayerControl;
