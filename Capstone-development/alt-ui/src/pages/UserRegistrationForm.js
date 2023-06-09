import { useState, useEffect, useContext } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { authenticate } from "../services/authService";
import AuthContext from "../contexts/AuthContext";
import { LockClosedIcon } from "@heroicons/react/outline";
import ff_logo2 from "../images/ff_logo2.png";

export default function UserRegistrationForm() {
  useEffect(() => {
    document.body.classList.add("bg");
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
                Register
              </h2>
            </div>
            <form
              className="mt-8 space-y-6"
              onSubmit={handleSubmit}
              method="POST"
            >
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
                  Register
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
}
