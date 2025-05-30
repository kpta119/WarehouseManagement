import createCRUD from "./crud";

const categoriesAPI = createCRUD("categories", {
  get: true,
  create: true,
  update: true,
  delete: true,
});

export default categoriesAPI;
