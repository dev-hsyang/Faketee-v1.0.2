import React from 'react';
import { Link } from 'react-router-dom';

import { ButtonProps } from './types';
import useButtonHeirarchy from './Hooks/useHeirarchy';

type ButtonReturn = (props: ButtonProps) => React.ReactElement;

export const Button: ButtonReturn = (props: ButtonProps) => {
  const { children, kind = 'button', href, type = 'button', step = 'first' } = props;
  const commonStyle =
    'hover:animate-push active:animate-pull w-full font-semibold px-10 py-3 shadow-md rounded-md';
  const heirarchy = useButtonHeirarchy(step);
  const style = `${commonStyle} ${heirarchy}`;

  return (
    <>
      {kind === 'a' && (
        <Link className={style} to={href!}>
          {children}
        </Link>
      )}
      {kind === 'btn' && (
        <button className={style} type={type}>
          {children}
        </button>
      )}
    </>
  );
};

export default Button;
