import { FC } from 'react';
import type { Page } from '../pages';
import { useLocation, type Location } from 'react-router-dom';

interface HeaderProps {
  links: Page[];
}

const findTitle = (links: Page[], location: Location): string | undefined => {
  return links.find((page) => page.path === location.pathname)?.label;
}

const Header: FC<HeaderProps> = ({ links }) => {
  const location = useLocation()
  const title = findTitle(links, location)
  const nav = (
    <nav className="w-full flex py-4">
      {links.map((link, index) => (
        link.label ?
          <a
            key={index}
            href={link.path}
            className="mr-4 px-2 border border-gray-600 rounded-md hover:text-gray-900"
          >
            {link.label}
          </a> :
          null
      ))}
    </nav>
  )
  return <>
    { nav }
    { title && <header> <h1>{title}</h1> </header>}
  </>
};

export default Header;
