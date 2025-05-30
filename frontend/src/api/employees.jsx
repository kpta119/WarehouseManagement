import createCRUD from "./crud";

const employeesAPI = createCRUD("employees", {
  get: true,
  getId: true,
  create: true,
});

export default employeesAPI;
