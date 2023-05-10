import { Fragment, useState, useContext } from "react";
import { Link, NavLink, useLocation } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";
import { Disclosure, Menu, Transition } from "@headlessui/react";
import { MenuIcon, XIcon } from "@heroicons/react/outline";
import { UserIcon } from "@heroicons/react/solid";
import ff_logo from "../images/ff_logo.png";

const navigation = [
  { name: "Home", href: "/" },
  { name: "About Us", href: "/aboutus" },
  { name: "Recipes", href: "/recipes" },
  { name: "Contact", href: "/Contact" },
  { name: "Recipebook", href: "/recipebook"}
];

export default function NavBar() {
  function classNames(...classes) {
    return classes.filter(Boolean).join(" ");
  }
  const [nav, setNav] = useState(false);

  const handleNav = () => {
    setNav(!nav);
  };

  const location = useLocation();
  const { user, logout } = useContext(AuthContext);

  function handleLogout(evt) {
    evt.preventDefault();
    logout();
  }
  return (
    <Disclosure as="nav" className="sticky top-0 bg-green-900 z-50">
      {({ open }) => (
        <>
          <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
            <div className="relative flex h-16 items-center justify-between">
              <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                {/* Mobile menu button*/}
                <Disclosure.Button className="inline-flex items-center justify-center rounded-md p-2 text-gray-400 hover:bg-green-300 hover:text-white focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                  <span className="sr-only">Open main menu</span>
                  {open ? (
                    <XIcon className="block h-6 w-6" aria-hidden="true" />
                  ) : (
                    <MenuIcon className="block h-6 w-6" aria-hidden="true" />
                  )}
                </Disclosure.Button>
              </div>
              <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
                <div className="flex flex-shrink-0 items-center">
                  <a href="/" className="block">
                    <img
                      className="block h-10 w-auto lg:hidden"
                      src={ff_logo}
                      alt="Fresh Feast"
                    />
                    <img
                      className="hidden h-10 w-auto lg:block"
                      src={ff_logo}
                      alt="Fresh Feast"
                    />
                  </a>
                </div>
                <div className="hidden sm:ml-6 sm:block">
                  <div className="flex space-x-4">
                    {navigation.map((item) => (
                      <Link
                        key={item.name}
                        to={item.href}
                        className={classNames(
                          item.current
                            ? "bg-gray-900 text-white"
                            : "text-gray-300 hover:bg-green-300 hover:text-white",
                          "rounded-md px-3 py-2 text-sm font-medium"
                        )}
                        aria-current={item.current ? "page" : undefined}
                      >
                        {item.name}
                      </Link>
                    ))}
                  </div>
                </div>
              </div>
              <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                {/* Profile dropdown */}
                <Menu as="div" className="relative ml-3">
                  <Menu.Button className="flex rounded-full bg-green-900 text-sm focus:solid-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-300">
                    <span className="sr-only">Open user menu</span>
                    <div>
                      <button
                        type="button"
                        className="rounded-full bg-green-900 p-1 text-gray-300 hover:text-white focus:solid-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-300"
                      >
                        <span className="sr-only">View notifications</span>
                        <UserIcon className="h-6 w-6" aria-hidden="true" />
                      </button>
                    </div>
                  </Menu.Button>

                  <Transition
                    as={Fragment}
                    enter="transition ease-out duration-100"
                    enterFrom="transform opacity-0 scale-95"
                    enterTo="transform opacity-100 scale-100"
                    leave="transition ease-in duration-75"
                    leaveFrom="transform opacity-100 scale-100"
                    leaveTo="transform opacity-0 scale-95"
                  >
                    <Menu.Items className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                      {user ? (
                        <>
                          <Menu.Item>
                            <NavLink
                              to={"/logout"}
                              className="p-4"
                              onClick={handleLogout}
                            >
                              Logout
                            </NavLink>
                          </Menu.Item>
                        </>
                      ) : (
                        <>
                          <Menu.Item>
                            <NavLink className="p-4" to="/login">
                              Login
                            </NavLink>
                          </Menu.Item>
                          <Menu.Item>
                            <NavLink className="p-4" to="/register">
                              Register
                            </NavLink>
                          </Menu.Item>
                        </>
                      )}
                    </Menu.Items>
                  </Transition>
                </Menu>
              </div>
            </div>
          </div>

          <Disclosure.Panel className="sm:hidden">
            <div className="space-y-1 px-2 pb-3 pt-2">
              {navigation.map((item) => (
                <Disclosure.Button
                  key={item.name}
                  as="a"
                  href={item.href}
                  className={classNames(
                    item.current
                      ? "bg-gray-900 text-white"
                      : "text-gray-300 hover:bg-gray-700 hover:text-white",
                    "block rounded-md px-3 py-2 text-base font-medium"
                  )}
                  aria-current={item.current ? "page" : undefined}
                >
                  {item.name}
                </Disclosure.Button>
              ))}
            </div>
          </Disclosure.Panel>
        </>
      )}
    </Disclosure>
  );
}
