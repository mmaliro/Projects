import * as base from "./baseService";
const model = "tags";

export function getEmptyTag() {
  return {
    tagId: 0,
    tagName: "",
    defaultImage: "",
  };
}

export async function findAll() {
  return base.findAll(model);
}

export async function findById(tagId) {
  return base.findById(model, tagId);
}

export async function save(tag) {
  return base.save(model, tag, tag.tagId);
}

export async function deleteById(tagId) {
  return base.deleteById(model, tagId);
}
