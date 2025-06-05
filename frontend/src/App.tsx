import "./App.css";
import Map from "./components/Map";
import "react-openlayers/dist/index.css"; // for css

function App() {
  return (
    <div className="w-screen h-screen bg-red-200">
      <Map />
    </div>
  );
}

export default App;
