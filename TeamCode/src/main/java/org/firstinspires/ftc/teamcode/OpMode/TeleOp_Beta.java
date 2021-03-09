package org.firstinspires.ftc.teamcode.OpMode;

//THIS IS A BETA VERSION FOR THE TELEOP CODE! ALL TESTINGS OCCUR HERE. NOT FOR USE IN COMPETITIONS!

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Arm;
import org.firstinspires.ftc.teamcode.Robot.Constants;
import org.firstinspires.ftc.teamcode.Trajectories.Coordinates;
import org.firstinspires.ftc.teamcode.Robot.Drivetrain;
import org.firstinspires.ftc.teamcode.Robot.Hopper;
import org.firstinspires.ftc.teamcode.Robot.Intake;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.Robot.Shooter;

@TeleOp(name = "TeleOp_Beta", group = "OpModes")
public class TeleOp_Beta extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot.initTeleOp(hardwareMap);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.

            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();
                Robot.drive.update();
                Robot.myLocalizer.update();
                Hopper.back();
                Pose2d myPose = Robot.myLocalizer.getPoseEstimate();

                telemetry.addData("x", myPose.getX());
                telemetry.addData("y", myPose.getY());
                telemetry.addData("heading", myPose.getHeading());

                Drivetrain.fieldCentricDrive(gamepad1.right_stick_x* Constants.turnPower,
                                            gamepad1.right_stick_y,
                                            gamepad1.left_trigger,
                                            gamepad1.right_trigger);
                if (gamepad1.y) {
                    Shooter.on(Constants.powerConstant);
                }
                if (gamepad1.a) {
                    Shooter.off();
                }
                if (gamepad1.left_bumper){ //code for speed change
                   Drivetrain.speedControl(0.4);
                }
                else{
                    Drivetrain.speedControl(1);
                }
                /*if (gamepad1.x){
                    Robot.alignStraight();
                    Robot.ShootGoal();
                }*/
                if(gamepad1.x){
                    telemetry.addData("Aligning to shoot", "");
                    Robot.myLocalizer.setPoseEstimate(Coordinates.shoot);
                    Robot.drive.setPoseEstimate(Coordinates.shoot);
                    Shooter.shootThree();
                }
                if(gamepad1.b){
                    telemetry.addData("Resetting odometry position", "");
                    Drivetrain.alignToShoot();

                }
                /*if (gamepad1.b){
                    Robot.alignStraight();
                    Robot.ShootOne();
                }*/
                if (gamepad1.right_bumper){ //code for shooting the power shots
                    Shooter.powerShot();
                }
                if (gamepad1.dpad_down){ // code for the arm
                    Arm.moveUp(1);
                }
                else if (gamepad1.dpad_up){
                    Arm.moveDown(1);
                }
                else{
                    Arm.stop();
                }
                if (gamepad1.dpad_right){
                    Arm.close();
                }
                if (gamepad1.dpad_left){
                    Arm.open();
                }
                Intake.succIn(gamepad1.left_stick_y);

            }

        }

    }


}