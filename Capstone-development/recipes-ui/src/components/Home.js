import { useState } from 'react';
import { Dialog } from '@headlessui/react';
import { XIcon } from '@heroicons/react/outline';
import ff_landingpage from '../videos/ff_landingpage.mp4';



export default function Home() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false)

  return (
    <div className='w-full h-screen relative'>
      <header className="absolute inset-x-0 top-0 z-[-1]">
        <nav className="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
          <div className="flex lg:flex-1">
            <video className='w-full h-screen object-cover z-[-1]' src={ff_landingpage} autoPlay loop muted />
             
          </div>
          
        </nav>
        <Dialog as="div" className="lg:hidden" open={mobileMenuOpen} onClose={setMobileMenuOpen}>
          <div className="fixed inset-0 z-50" />
          <Dialog.Panel className="fixed inset-y-0 right-0 z-50 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
            <div className="flex items-center justify-between">
              
              <button
                type="button"
                className="-m-2.5 rounded-md p-2.5 text-gray-700"
                onClick={() => setMobileMenuOpen(false)}
              >
                <span className="sr-only">Close menu</span>
                <XIcon className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>
            <div className="mt-6 flow-root">
              <div className="-my-6 divide-y divide-gray-500/10">
                
              </div>
            </div>
          </Dialog.Panel>
        </Dialog>
      </header>

      <div className="relative isolate px-6 pt-14 lg:px-8">
        
        <div className="mx-auto max-w-2xl py-32 sm:py-48 lg:py-56">
          <div className="hidden sm:mb-8 sm:flex sm:justify-center">
            <div className="relative rounded-full px-3 py-1 text-sm leading-6 text-white ring-1 ring-gray-900/10 hover:ring-gray-900/20">
            Unlock Delicious Recipes and Meal Planning Solutions!{' '}
              {<a href="/userregistration" className="font-semibold text-green-300">
                <span className="absolute inset-0" aria-hidden="true" />
                Start Here <span aria-hidden="true">&rarr;</span>
              </a> }
            </div>
          </div>
          <div className="text-center">
            <h1 className="text-4xl font-bold tracking-tight text-green-300 sm:text-6xl">
            Create Your Perfect Meal Plan with Fresh Feasts
            </h1>
            <p className="mt-6 text-lg leading-8 text-white">
            Fresh Feasts helps you to unlock delicious recipes and meal planning solutions that make creating delicious and healthy meals easy. With our recipe logs, you will be able to quickly and easily create your own personalized meal plan that fits your lifestyle. Whether you're looking for something quick and easy or a more complex concoction, we have something for everyone. 
            </p>
            <div className="mt-10 flex items-center justify-center gap-x-6">
              <a
                href="/userlogin"
                className="rounded-md bg-green-900 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-green-300 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-green-900"
              >
                Login
              </a>
             
            </div>
          </div>
        </div>
      
      </div>
    </div>
  )
}
