import createCRUD from "./crud";

const productsAPI = {
  ...createCRUD("products", {
    get: true,
    getId: true,
    create: true,
    update: true,
    delete: true,
  }),
};

export default productsAPI;
