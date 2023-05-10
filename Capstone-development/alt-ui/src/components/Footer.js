import React from 'react';
import footer_logo from "../images/footer_logo.png";

function Footer() {
  return (
    <footer className="bg-green-900 text-white py-6 z-50 absolute bottom-0 w-full text-center">
      {/* <img src={footer_logo} alt="Footer Logo" className="mx-auto mb-2" /> */}
      <ul className="text-sm mb-2 flex justify-center">
        <li className="mr-2"><a href="#">Terms & Conditions |</a></li>
        <li className="mr-2"><a href="#">Privacy Policy |</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
      <p className="text-sm text-gray-400">&copy; 2023 Fresh Feasts. All rights reserved.</p>
    </footer>
  );
}

export default Footer;



