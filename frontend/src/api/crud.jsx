import apiClient from "../utils/apiClient";

const createCRUD = (resource, options) => {
  const base = `/api/${resource}`;
  return {
    list: (params) => options.get && apiClient.get(base, { params }),
    getId: (id) => options.getId && apiClient.get(`${base}/${id}`),
    create: (data) => options.create && apiClient.post(base, data),
    update: (id, data) =>
      options.update && apiClient.put(`${base}/${id}`, data),
    delete: (id) => options.delete && apiClient.delete(`${base}/${id}`),
  };
};

export default createCRUD;
