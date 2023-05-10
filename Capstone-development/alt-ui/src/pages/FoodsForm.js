import { useEffect, useState } from "react";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import { findById, getEmptyRecipe, save } from "../services/recipeService";
import { findAll } from "../services/foodService";
import { CBox, DropDown } from "../components";

function FoodsForm() {
  const [currentRecipe, setCurrentRecipe] = useState(getEmptyRecipe());
  const [errors, setErrors] = useState([]);
  const [allFoods, setAllFoods] = useState([]);
  const [wait, setWait] = useState(true);

  const navigate = useNavigate();
  const { recipeId } = useParams();

  useEffect(() => {
    findAll()
      .then((result) => {
        setAllFoods(result);
        setWait(false);
      })
      .catch(() => navigate("/"));
  }, [navigate]);

  useEffect(() => {
    if (recipeId) {
      findById(recipeId)
        .then((recipe) => {
          setCurrentRecipe(recipe);
          setWait(false);
        })
        .catch(() => navigate("/recipes"));
    } else {
      setWait(false);
    }
  }, [recipeId, navigate]);

  function handleChange(evt) {
    const nextRecipe = { ...currentRecipe };
    nextRecipe[evt.target.name] = evt.target.value;
    setCurrentRecipe(nextRecipe);
  }

  function handleSubmit(evt) {
    evt.preventDefault();
    save(currentRecipe)
      .then(() => navigate("/recipes"))
      .catch((errs) => {
        if (errs) {
          setErrors(errs);
        } else {
          navigate("/recipes");
        }
      });
  }

  if (wait) {
    return null;
  }
  const measurements = [
    { name: "cup" },
    { name: "tsp" },
    { name: "tbsp" },
    { name: "pinch" },
    { name: "oz" },
    { name: "fl. oz" },
    { name: "qt" },
    { name: "pt" },
    { name: "gal" },
    { name: "lb" },
    { name: "ml" },
    { name: "g" },
    { name: "kg" },
    { name: "liter" },
    { name: "stick" },
    { name: "slice" },
  ];

  return (
    <div className="min-h-screen p-6 bg-gray-100 flex justify-center">
      <div className="container max-w-screen-lg mx-auto">
        <div>
          <h2 className="font-semibold text-xl text-gray-600">Add A Food</h2>
          <div className="bg-white rounded shadow-lg p-4 px-4 md:p-8 mb-6">
            <form onSubmit={handleSubmit}>
              <div className="grid gap-4 gap-y-2 text-sm grid-cols-1 lg:grid-cols-3">
                <div className="lg:col-span-2 mb-4">
                  <div className="grid gap-4 gap-y-2 text-sm grid-cols-1 md:grid-cols-5">
                    <div className="md:col-span-5">
                      <label className="form-label" htmlFor="title">
                        Food
                      </label>
                      <CBox array={allFoods} name="foodName" id="foodId" />
                    </div>
                    <div className="md:col-span-2">
                      <label className="form-label" htmlFor="cookTime">
                        Amount
                      </label>
                      <input
                        type="number"
                        name="amount"
                        id="amount"
                        className="form-control h-10 border mt-1 rounded px-4 w-full bg-gray-50"
                        onChange={handleChange}
                        // value={currentRecipe.cookTime}
                        required
                        placeholder=""
                      />
                    </div>
                    <div className="md:col-span-5">
                      <label className="form-label" htmlFor="title">
                        Measurement Unit{" "}
                      </label>
                      <DropDown array={measurements} name="name" id="name" />
                    </div>
                    <NavLink
                      to="/recipes"
                      className="rounded-md bg-[#6a8f6b] mx-2 mt-4 py-2 px-3.5 text-sm font-semibold text-white shadow-sm hover:bg-green-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-900"
                    >
                      Done
                    </NavLink>
                    <button
                      type="submit"
                      className="rounded-md bg-[#6a8f6b] mx-2 mt-4 py-2 px-3.5 text-sm font-semibold text-white shadow-sm hover:bg-green-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-900"
                    >
                      Add Food
                    </button>
                    <NavLink
                      to="/recipes"
                      className="rounded-md bg-[#6a8f6b] mx-2 mt-4 py-2 px-3.5 text-sm font-semibold text-white shadow-sm hover:bg-green-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-900"
                    >
                      Cancel
                    </NavLink>
                    {errors.length > 0 && (
                      <div className="alert alert-danger mt-2">
                        <ul>
                          {errors.map((err) => (
                            <li key={err}>{err}</li>
                          ))}
                        </ul>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default FoodsForm;
