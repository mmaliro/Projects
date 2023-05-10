import mmProfile from '../images/mm_profile.jpg';
import img_logo from '../images/img_logo.png';
import { useEffect } from 'react';

const people = [
  {
    name: 'Mwenge Maliro',
    role: 'Full-Stack Developer',
    imageUrl: mmProfile,
    socialMediaUrl: 'https://www.linkedin.com/in/mwenge-maliro',
    socialMediaLogoUrl: img_logo,
  },
  {
    name: 'Paul Mitchell',
    role: 'Full-Stack Developer',
    imageUrl: '',
    socialMediaUrl: 'https://www.linkedin.com/in/paul-mitchell',
    socialMediaLogoUrl: img_logo,
  },
  {
    name: 'Hanako Boucher',
    role: 'Full-Stack Developer',
    imageUrl: '',
    socialMediaUrl: 'https://www.linkedin.com/in/hanako-boucher',
    socialMediaLogoUrl: img_logo,
  },
];

export default function AboutUs() {
  useEffect (() => {
    document.body.classList.add ("bg")

  },[]) 

  return (
 <div className="bg-cover bg-center w-full h-screen"> 
    //<div className="bg-white py-24 sm:py-32 flex flex-col justify-center items-center">
      <h2 className="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">Meet Our Team</h2>
      <div className="max-w-2xl mt-6">
        <p className="text-lg leading-8 text-gray-600">
          When it comes to creating a successful web app, the team behind it is just as important as the technology they use.
          That's why we are excited to introduce you to the talented trio responsible for bringing Fresh Foods to life.
          Mwenge, Paul, and Hanako are the full-stack developers behind this innovative platform.
          Mwenge utilized her skills to craft the intuitive front-end, while Paul tackled the complex back-end programming.
          And, of course, none of this would be possible without the expertise of Hanako, who masterfully designed the database.
          We are grateful to have such a dedicated team working to provide food lovers with a seamless and satisfying online experience.
        </p>
      </div>
      <div className="mx-auto grid max-w-7xl gap-x-8 gap-y-20 px-6 lg:px-8 xl:grid-cols-3 mt-6">
        <ul
          role="list"
          className="mx-auto grid gap-x-8 gap-y-12 sm:grid-cols-3 sm:gap-y-16 xl:col-span-3"
        >
          {people.map((person) => (
            <li key={person.name}>
              <div className="flex items-center gap-x-6">
                <img className="h-16 w-16 rounded-full" src={person.imageUrl} alt="" />
                <div>
                  <h3 className="text-base font-semibold leading-7 tracking-tight text-gray-900">{person.name}</h3>
                  <p className="text-sm font-semibold leading-6 text-indigo-600">{person.role}</p>
                  <a href={person.socialMediaUrl} className="text-sm font-semibold leading-6 text-indigo-600 hover:underline" target="_blank" rel="noopener noreferrer">
                    <img src={person.socialMediaLogoUrl} alt="" className="h-4 w-4 inline-block" />
                  </a>
                </div>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  </div>
  );
}
