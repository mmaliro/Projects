import { LockClosedIcon } from "@heroicons/react/outline";
import ff_logo2 from "../images/ff_logo2.png";
import { useContext, useEffect, useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { authenticate } from "../services/authService";
import AuthContext from "../contexts/AuthContext";
import login_page from "../images/login_page.jpg"

export default function UserLogin() {
  useEffect(() => {
    document.body.classList.add("recipe");
  }, []);

  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const [hasError, setHasError] = useState(false);
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  function handleChange(evt) {
    const next = { ...credentials };
    next[evt.target.name] = evt.target.value;
    setCredentials(next);
  }

  function handleSubmit(evt) {
    evt.preventDefault();
    authenticate(credentials)
      .then((user) => {
        login(user);
        navigate("/");
      })
      .catch(() => setHasError(true));
  }

  return (
    <>
      <div className="bg-cover bg-center w-full h-screen">
        <div className="flex min-h-full items-center justify-center px-4 py-12 sm:px-6 lg:px-8">
          <div className="w-full max-w-md space-y-8">
            <div>
              <img
                className="mx-auto h-12 w-auto"
                src={ff_logo2}
                alt="Your Company"
              />
              <h2 className="mt-6 text-center text-3xl font-bold tracking-tight text-gray-900">
                Sign In
              </h2>
              <p className="mt-2 text-center text-sm text-gray-600">
                {/* To DO this has no active link convert to NavLink*/}
                <a
                  href="#"
                  className="font-medium text-green-700 hover:text-green-300"
                >
                  Sign Up Here
                </a>
              </p>
            </div>
            <form
              className="mt-8 space-y-6"
              onSubmit={handleSubmit}
              method="POST"
            >
              <input type="hidden" name="remember" defaultValue="true" />
              <div className="-space-y-px rounded-md shadow-sm">
                <div>
                  <label htmlFor="email-address" className="sr-only">
                    Email address
                  </label>
                  <input
                    id="email-address"
                    name="username"
                    type="text"
                    autoComplete="username"
                    required
                    className="form-control ps-2 relative block w-full rounded-t-md border-0 py-1.5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-green-900 sm:text-sm sm:leading-6"
                    placeholder="User Name"
                    value={credentials.username}
                    onChange={handleChange}
                  />
                </div>
                <div>
                  <label htmlFor="password" className="sr-only">
                    Password
                  </label>
                  <input
                    id="password"
                    name="password"
                    type="password"
                    autoComplete="current-password"
                    required
                    className="form-control ps-2 relative block w-full rounded-b-md border-0 py-1.5 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-green-900 sm:text-sm sm:leading-6"
                    placeholder="Password"
                    value={credentials.password}
                    onChange={handleChange}
                  />
                </div>
              </div>

              <div className="flex items-center justify-between">
                <div className="flex items-center">
                  <input
                    id="remember-me"
                    name="remember-me"
                    type="checkbox"
                    className="h-4 w-4 rounded border-gray-300 text-green-900 focus:ring-green-900"
                  />
                  <label
                    htmlFor="remember-me"
                    className="ml-2 block text-sm text-gray-900"
                  >
                    Remember me
                  </label>
                </div>
                {/* To DO this has no active link convert to NavLink*/}
                <div className="text-sm">
                  <a
                    href="#"
                    className="font-medium text-green-700 hover:text-green-300"
                  >
                    Forgot your password?
                  </a>
                </div>
              </div>

              <div>
                <button
                  type="submit"
                  className="group relative flex w-full justify-center rounded-md bg-green-900 px-3 py-2 text-sm font-semibold text-white hover:bg-green-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-igreen-900"
                >
                  <span className="absolute inset-y-0 left-0 flex items-center pl-3">
                    <LockClosedIcon
                      className="h-5 w-5 text-[green-700] group-hover:text-green-300"
                      aria-hidden="true"
                    />
                  </span>
                  Sign In
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
