import createCRUD from "./crud";

const warehousesAPI = createCRUD("warehouses", {
  get: true,
  getId: true,
  create: true,
  update: true,
  delete: true,
});

export default warehousesAPI;
