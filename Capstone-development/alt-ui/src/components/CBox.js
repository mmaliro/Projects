import { useState } from "react";
import { Combobox, Transition } from "@headlessui/react";
import { CheckIcon } from "@heroicons/react/solid";

export default function CBox({ array, name, id }) {
  const [selectedElement, setSelectedElement] = useState();
  const [query, setQuery] = useState("");

  const filteredElements =
    query === ""
      ? array
      : array.filter((item) => {
          console.log(item);
          return item[name].toLowerCase().includes(query.toLowerCase());
        });

  function onChange(value) {
    console.log(value);
  }

  return (
    <div className="md:col-span-5">
      <select
        id="countries"
        value={selectedElement}
        by="id"
        onChange={setSelectedElement}
        className="form-control h-10 border mt-1 rounded px-4 w-full bg-gray-50"
      >
        <option selected></option>
        {filteredElements.map((element) => (
          <option key={element[id]} value={element}>
            {element[name]}
          </option>
        ))}
      </select>

      <Combobox value={selectedElement} onChange={onChange}>
        <Combobox.Input
          type="text"
          name="meaurementUnit"
          id="meaurementUnit"
          className="form-control h-10 border mt-1 rounded px-4 w-full bg-gray-50"
          value={query}
          required
          placeholder=""
          onChange={(event) => setQuery(event.target.value)}
        />
        <Combobox.Options className="py-2 text-sm">
          {filteredElements.map((element) => (
            <Combobox.Option key={element[id]} value={element}>
              <CheckIcon className="hidden ui-selected:block" />
              {element[name]}
            </Combobox.Option>
          ))}
        </Combobox.Options>
      </Combobox>
    </div>
  );
}
