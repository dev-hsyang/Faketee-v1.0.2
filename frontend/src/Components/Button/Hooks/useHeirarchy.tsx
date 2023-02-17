export default function useButtonHeirarchy(input: string) {
  switch (input) {
    case 'first':
      return 'bg-primary-main text-white';
    case 'second':
      return 'bg-white border-2 border-primary-main shadow-none text-primary-main';
    case 'third':
      return 'shadow-none underline underline-offset-4 text-primary-main';
    case 'fourth':
      return 'text-black underline-offset-2 shadow-none';
    case 'fifth':
      return 'text-caption underline font-light text-sm shadow-none';
    default:
      return '';
  }
}
