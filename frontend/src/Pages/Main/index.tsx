import Button from '@/Components/Button';

import { MainPageWrapper } from './style';

export default function MainPage() {
  return (
    <MainPageWrapper>
      <h1 className="font-bold mb-60">시프티에 오신 것을 환영합니다</h1>
      <div className="w-full flex flex-col gap-4">
        <div className="flex flex-col gap-4 w-full">
          <Button kind="a" href="/account/register-form">
            가입하기
          </Button>
          <Button kind="a" href="/account/login-form">
            로그인하기
          </Button>
        </div>
        <span>&copy; faketee2022</span>
      </div>
    </MainPageWrapper>
  );
}
