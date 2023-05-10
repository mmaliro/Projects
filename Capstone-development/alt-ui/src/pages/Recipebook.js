import { useEffect, useState, useContext } from "react";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import { RecipeCard, RecipeGrid } from "../components";
import { findById } from "../services/appUserService";

import AuthContext from "../contexts/AuthContext";

export default function Recipebook() {

    const [userRecipes, setUserRecipes] = useState([]);
    const [wait, setWait] = useState(true);
    const { user } = useContext(AuthContext);
    const navigate = useNavigate();

    // if (!user) {
    //     navigate("/");
    //     }
  
    useEffect(() => {
    if(user) {
      findById(user.appUserId)
        .then((result) => {
            setUserRecipes(result.recipes);
            setWait(false);
        })
    }}, []);

if (user && wait) {
    return (
        <div className="spinner-border" role="status">
        <span className="visually-hidden">Loading...</span>
        </div>
    );
    }

    return (
    <>
        <div className="">
        <div className="">
            <div className="col d-flex justify-content-end align-items-center">
                {user ? <RecipeGrid array={userRecipes}/> : navigate("/") }
            </div>
        </div>
        </div>
    </>
    );

}
