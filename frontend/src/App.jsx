import { Provider } from "react-redux";
import store from "./app/store";
import AppRouter from "./routes/AppRouter";
import "./assets/styles/global.css";

const App = () => (
  <Provider store={store}>
    <AppRouter />
  </Provider>
);

export default App;
