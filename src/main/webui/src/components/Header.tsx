import { FC } from 'react';
import type { Page } from '../pages';
import { useLocation, type Location } from 'react-router-dom';
import { useErrorBoundary } from 'react-error-boundary';

interface HeaderProps {
  links: Page[];
}

const findTitle = (links: Page[], location: Location): string | undefined => {
  return links.find((page) => page.path === location.pathname)?.label;
}

const Header: FC<HeaderProps> = ({ links }) => {
  const location = useLocation()
  const { showBoundary } = useErrorBoundary()
  const title = findTitle(links, location)
  if (!title) {
    showBoundary(new Error('No title found'))
    return
  }
  return <>
    <nav className="w-full flex py-4">
      {links.map((link, index) => (
        <a
          key={index}
          href={link.path}
          className="mx-4 px-2 border border-gray-600 rounded-md hover:text-gray-900"
        >
          {link.label}
        </a>
      ))}
    </nav>
    <header>
      <h1>{title}</h1>
    </header>
  </>
};

export default Header;
