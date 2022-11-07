package baseball;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    @Test
    void 게임종료_후_정상종료() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("687", "356", "724", "219", "917", "719", "2");
                    assertThat(output()).contains("1볼", "낫싱", "1스트라이크", "2스트라이크", "2볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                7, 1, 9
        );
    }

    @Test
    void 게임종료_후_재시작() {
        assertRandomNumberInRangeTest(
                () -> {
                    run("246", "135", "1", "597", "589", "2");
                    assertThat(output()).contains("낫싱", "3스트라이크", "1볼 1스트라이크", "3스트라이크", "게임 종료");
                },
                1, 3, 5, 5, 8, 9
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 예외_테스트_잘못된옵션입력() {
        assertRandomNumberInRangeTest(
                () -> {
                    assertThatThrownBy(() -> runException("246", "432", "3"))
                            .isInstanceOf(IllegalArgumentException.class);
                },
                4, 3, 2
        );
    }

    @Test
    void 예외_테스트_잘못된숫자입력_0이포함된경우() {
        assertThatThrownBy(() -> runException("102"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 예외_테스트_잘못된숫자입력_중복된숫자() {
        assertThatThrownBy(() -> runException("998"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    class ResultTest {
        @Test
        void 잘못된점수_음수_테스트() {
            assertThatThrownBy(() -> Result.getResult(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 잘못된점수_enum에_포함되지_않는경우_테스트() {
            assertThatThrownBy(() -> Result.getResult(4))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class StatusTest {
        @Test
        void 잘못된옵션_음수_테스트() {
            assertThatThrownBy(() -> Status.getStatus(-1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 잘못된옵션_2보다_큰수_테스트() {
            assertThatThrownBy(() -> Status.getStatus(3))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
