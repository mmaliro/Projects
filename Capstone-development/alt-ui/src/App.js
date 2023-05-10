import { useEffect, useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { refresh } from "./services/authService";
import AuthContext from "./contexts/AuthContext";
import {
  AboutUs,
  Contact,
  FoodsForm,
  Home,
  UserLogin,
  Recipes,
  RecipeDetails,
  RecipeForm,
  UserRegistrationForm,
  Recipebook
} from "./pages";
import { Navbar } from "./components";
import Footer from "./components/Footer";

function App() {
  const [user, setUser] = useState();

  useEffect(() => {
    refresh().then(login).catch();
  }, []);

  function login(userArg) {
    setUser(userArg);
    localStorage.setItem("Recipe_JWT", userArg.jwt);
  }

  function logout() {
    setUser();
    localStorage.removeItem("Recipe_JWT");
  }

  const auth = {
    user,
    login,
    logout,
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <Navbar />
        <Footer />
        <div className="mb-5 mt-2">
          <Routes>
            <Route path="/aboutus" element={<AboutUs />} />
            <Route path="/" element={<Home />} />
            <Route path="/recipes">
              <Route path="add" element={<RecipeForm />} />
              <Route index element={<Recipes />} />
              <Route path=":recipeId">
                <Route index element={<RecipeDetails />} />
                <Route path="edit" element={<RecipeForm />} />
                <Route path="food" element={<FoodsForm />} />
              </Route>
            </Route>
            <Route path="/recipebook" element={<Recipebook />}/>
            <Route path="/contact" element={<Contact />} />
            <Route path="/login" element={<UserLogin />} />
            <Route path="/register" element={<UserRegistrationForm />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;