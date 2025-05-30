import createCRUD from "./crud";

const suppliersAPI = createCRUD("suppliers", {
  get: true,
  getId: true,
  create: true,
});

export default suppliersAPI;
