import createCRUD from "./crud";

const transactionsAPI = createCRUD("transactions", {
  get: true,
  getId: true,
});

export default transactionsAPI;
