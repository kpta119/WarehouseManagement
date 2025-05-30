import createCRUD from "./crud";

const clientsAPI = createCRUD("clients", {
  get: true,
  getId: true,
  create: true,
});

export default clientsAPI;
